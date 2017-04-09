package org.springframework.web.servlet.tags.form;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@SuppressWarnings("serial")
public class RecursiveOptionsTag extends OptionsTag {
    private ExpressionParser parser;

    private Object rootItems;
    private String propertyOfChildren;
    private String itemLabelExpression;
    private String indentString;

    public RecursiveOptionsTag() {
        parser = new SpelExpressionParser();
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        processRecursively(tagWriter, rootItems, 0);
        return SKIP_BODY;
    }

    private void processRecursively(TagWriter tagWriter, Object items, int indent) throws JspException {
        try {
            for (Object item : (Iterable<?>) items) {
                renderItem(tagWriter, item, indent);
                List<?> children = getProperty(item, propertyOfChildren);
                processRecursively(tagWriter, children, indent + 1);
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new JspException("Cannot invoke property '" + propertyOfChildren + "' on '" + items + "'", e);
        }
    }

    private List<?> getProperty(Object object, String propertyName)
            throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        PropertyDescriptor propertyDescriptor = Arrays.stream(propertyDescriptors)
                .filter(pd -> pd.getName().equalsIgnoreCase(propertyName)).findFirst().get();

        Object value = propertyDescriptor.getReadMethod().invoke(object);

        List<?> list = (List<?>) value;
        return list;
    }

    private void renderItem(TagWriter tagWriter, Object item, int indent) throws JspException {
        String valueProperty = getItemValue();
        String labelExpression = getItemLabelExpression();

        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(item);
        Object value = wrapper.getPropertyValue(valueProperty);
        String label = evaluateExpression(item, labelExpression);
        label = generateIndent(indent) + label;

        RecursiveOptionWriter optionWriter = new RecursiveOptionWriter(getBindStatus(), isHtmlEscape());
        optionWriter.renderOption(tagWriter, item, value, label);
    }

    private String evaluateExpression(Object item, String labelExpression) {
        Expression expression = parser.parseExpression(labelExpression);
        EvaluationContext context = new StandardEvaluationContext(item);
        String label = (String) expression.getValue(context);
        return label;
    }

    private String generateIndent(int indent) {
        String result = "";
        for (int i = 0; i < indent; i++) {
            result += indentString;
        }
        return result;
    }

    public Object getRootItems() {
        return rootItems;
    }

    public void setRootItems(Object rootItem) {
        this.rootItems = rootItem;
    }

    public String getPropertyOfChildren() {
        return propertyOfChildren;
    }

    public void setPropertyOfChildren(String propertyOfChildren) {
        this.propertyOfChildren = propertyOfChildren;
    }

    public String getItemLabelExpression() {
        return itemLabelExpression;
    }

    public void setItemLabelExpression(String itemLabelExpression) {
        this.itemLabelExpression = itemLabelExpression;
    }

    public String getIndentString() {
        return indentString;
    }

    public void setIndentString(String indentString) {
        this.indentString = indentString;
    }
}
