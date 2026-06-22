# ab-test-ui-automation

Selenium tests for PDP with A/B experiment variants.

See [STRATEGY.md](STRATEGY.md) for approach.

## Run

```
mvn test
mvn test -Dgroups=p0
```

Java 17, Maven, Chrome.

## Project layout

- `experiments/` - variant intent, context, provider
- `pages/PdpPage` - product detail page
- `locators/MultiStrategyLocator` - finds elements when layout changes
- `api/CatalogClient` - reads product data from catalog json fixture (stand-in for catalog REST call)
- `tests/PdpCoreFlowTest` - main tests
- `fixtures/` - sample html for control vs sticky CTA

Fixtures are local html files. On staging you'd set the experiment cookie (or LD/Optimizely override) before opening the page, then read `data-variant` from the DOM to confirm.
