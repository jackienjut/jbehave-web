package org.jbehave.web.selenium;


import org.openqa.selenium.JavascriptExecutor;

public class SauceLabsContextView implements ContextView {

    ThreadLocal<String> currentScenario = new ThreadLocal<String>();

    private WebDriverProvider webDriverProvider;

    public SauceLabsContextView(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
        currentScenario.set("");
    }

    public void show(String scenario, String step) {
        sendContextMessage(scenario, step);
    }

    private void sendContextMessage(String scenario, String step) {
        try {
            JavascriptExecutor je = (JavascriptExecutor) webDriverProvider.get();
            if (!scenario.equals(currentScenario.get())) {
                je.executeScript("sauce:context=" + scenario);
                currentScenario.set(scenario);
            }
            je.executeScript("sauce:context=" + step);
        } catch (Exception e) {
            // fail silently.
        }
    }

    public void close() {
        sendContextMessage("", "JBehave closing ContextView");
    }
}