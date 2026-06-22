# Strategy - experiment-driven PDP testing

## Problem

PDP runs multiple A/B experiments at once - CTA placement, pricing block, recommendations. UI tests break when selectors assume a single layout. Failures often don't show which experiment was active.

## Variant handling

1. Build a `VariantIntent` with experiment key and expected variant
2. Set assignment cookie before navigation (LaunchDarkly / Optimizely on staging - cookie used here with local fixtures)
3. Load PDP, read `data-variant` from the DOM
4. Compare expected vs actual before running flows

## Test design

Tests go through page methods (`addToCart`, `getDisplayedPrice`) instead of layout-specific selectors.

`MultiStrategyLocator` tries sticky and normal locators in one wait cycle so CTA moves don't break the test.

Price on the page is compared to `CatalogClient` so we're not trusting DOM text alone.

## Variant explosion

**CI** - run control + treatment per experiment, not every combination. Re-run only the suites tied to flags that changed. Pairwise coverage when multiple experiments hit the same page.

**Code** - avoid a page class per combination. Split into components (CTA, pricing, recs) where each handles its own variants. Current code uses one `PdpPage` with multi-strategy locators; components are the next step when more experiments land on PDP.

**Scale** - experiment registry synced from LaunchDarkly/Optimizely, invariant smoke every PR, most checks in unit/API not browser.

## Layers

- UI: add-to-cart on control and sticky CTA (`addToCartOnControlAndStickyVariant`)
- API/unit: catalog price and price parser covered in same test class
- integration: UI price vs catalog checked in the main flow test

Groups: `p0` / `smoke` for PR, `exp-pdp-cta` when that flag changes.

## Flakiness

Explicit waits through `TestConfig`, no sleep. Variant verified before actions. New browser per test.

## CI

PR runs `mvn test -Dgroups=p0`. Flag change runs `mvn test -Dgroups=exp-pdp-cta`. Workflow in `.github/workflows/pr-smoke.yml`.

## Observability

`ExperimentFailureListener` prints experiment + variant on failure. `VariantContextHolder` holds context for the listener thread.

## Trade-offs

Forced flags in CI are stable but not the same as prod traffic - worth a small natural-bucketing run on schedule. Most validation should sit in API/unit layers to keep UI thin.
