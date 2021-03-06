package ru.colibri.ui.settings.ios;

import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import ru.colibri.ui.core.fields.IElement;
import ru.colibri.ui.core.finder.factories.ByFactory;

import static java.lang.String.format;

@Component
public class IOSByFactory extends ByFactory {

    private static String XPATH_TEMPLATE = "//*[contains(@name,'%1$s') or contains(@value,'%1$s') or contains(@label,'%1$s')]";


    public By byNameOrValueOrLabel(String label) {
        return By.xpath(createXPathByNameOrValueOrLabel(label));
    }

    private String createXPathByNameOrValueOrLabel(String label) {
        return format(XPATH_TEMPLATE, label);
    }

    @Override
    public By byElement(IElement element) {
        if (TextUtils.isEmpty(element.getXpath())) {
            if (TextUtils.isEmpty(element.getId())) {
                return byNameOrValueOrLabel(element.getText());
            } else {
                return byId(element.getId());
            }
        } else {
            return byXpath(element.getXpath());
        }
    }

    public By getNestedElements(IElement parent, IElement nested) {
        return By.xpath(createSearchXpath(parent) + createSearchXpath(nested));
    }

    private String createSearchXpath(IElement element) {
        if (!TextUtils.isEmpty(element.getXpath())) {
            return element.getXpath();
        } else {
            if (!TextUtils.isEmpty(element.getId())) {
                return createXPathByNameOrValueOrLabel(element.getId());
            } else {
                return createXPathByNameOrValueOrLabel(element.getText());
            }
        }
    }
}
