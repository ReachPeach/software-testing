const {test, expect} = require('@playwright/test');

test.only('e2e test', async ({page}) => {
    const URL = 'http://localhost:3000/';
    await page.goto(URL);

    await page.locator('.Author a[data-testid="40"]').first().evaluate(e => e.click());
    expect(page.url()).toEqual("http://localhost:3000/author/40");
});