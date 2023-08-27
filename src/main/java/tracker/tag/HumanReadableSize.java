package tracker.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import tracker.util.FormattingUtils;

@SuppressWarnings("serial")
public class HumanReadableSize extends TagSupport {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            String formattedValue = FormattingUtils.humanReadableSize(value);
            pageContext.getOut().print(formattedValue);
        } catch (IOException e) {
            throw new JspTagException(e.toString(), e);
        }
        return super.doEndTag();
    }
}
