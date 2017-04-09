/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.servlet.tags.form;

import java.beans.PropertyEditor;
import javax.servlet.jsp.JspException;

import org.springframework.util.Assert;
import org.springframework.web.servlet.support.BindStatus;

/**
 * Extended version of
 * {@link org.springframework.web.servlet.tags.form.OptionWriter} from Spring
 * Framework to support Options tree presentation.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Scott Andrews
 * @author SunriseCoder
 * @since 2.0
 */
class RecursiveOptionWriter {

    private final BindStatus bindStatus;

    private final boolean htmlEscape;

    /**
     * Creates a new {@code OptionWriter} for the supplied {@code objectSource}.
     *
     * @param bindStatus
     *            the {@link BindStatus} for the bound value (never
     *            {@code null})
     * @param htmlEscape
     *            escape HTML special chars
     */
    public RecursiveOptionWriter(BindStatus bindStatus, boolean htmlEscape) {
        Assert.notNull(bindStatus, "'bindStatus' must not be null");
        this.bindStatus = bindStatus;
        this.htmlEscape = htmlEscape;
    }

    /**
     * Renders an HTML '{@code option}' with the supplied value and label. Marks
     * the value as 'selected' if either the item itself or its value match the
     * bound value.
     */
    void renderOption(TagWriter tagWriter, Object item, Object value, Object label) throws JspException {
        tagWriter.startTag("option");

        String valueDisplayString = getDisplayString(value);
        String labelDisplayString = getDisplayString(label);

        valueDisplayString = processOptionValue(valueDisplayString);

        // allows render values to handle some strange browser compat issues.
        tagWriter.writeAttribute("value", valueDisplayString);

        if (isOptionSelected(value) || (value != item && isOptionSelected(item))) {
            tagWriter.writeAttribute("selected", "selected");
        }
        if (isOptionDisabled()) {
            tagWriter.writeAttribute("disabled", "disabled");
        }

        tagWriter.appendValue(labelDisplayString);
        tagWriter.endTag();
    }

    /**
     * Determines the display value of the supplied {@code Object}, HTML-escaped
     * as required.
     */
    private String getDisplayString(Object value) {
        PropertyEditor editor = (value != null ? this.bindStatus.findEditor(value.getClass()) : null);
        return ValueFormatter.getDisplayString(value, editor, this.htmlEscape);
    }

    /**
     * Process the option value before it is written. The default implementation
     * simply returns the same value unchanged.
     */
    protected String processOptionValue(String resolvedValue) {
        return resolvedValue;
    }

    /**
     * Determine whether the supplied values matched the selected value.
     * Delegates to {@link SelectedValueComparator#isSelected}.
     */
    private boolean isOptionSelected(Object resolvedValue) {
        return SelectedValueComparator.isSelected(this.bindStatus, resolvedValue);
    }

    /**
     * Determine whether the option fields should be disabled.
     */
    protected boolean isOptionDisabled() throws JspException {
        return false;
    }
}
