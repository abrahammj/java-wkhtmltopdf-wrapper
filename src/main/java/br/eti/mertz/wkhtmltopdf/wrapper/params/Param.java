package br.eti.mertz.wkhtmltopdf.wrapper.params;

public class Param {

    private String key;

    private String value;
    
    private boolean isGlobal;

    public Param(String key, String value) {
        this(key, value, true);
    }
    
    public Param(String key, String value, boolean isGlobal) {
        this.key = key;
        this.value = value;
        this.isGlobal = isGlobal;
    }

    public Param(String key) {
        this(key, null);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void setGlobal(boolean isGlobal) {
    	this.isGlobal = isGlobal;
    }
    
    public boolean isGlobal() {
    	return this.isGlobal;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder().append(Symbol.separator)
                .append(Symbol.param).append(key);
        if (value != null)
            sb.append(Symbol.separator).append(value);
        return sb.toString();
    }

}
