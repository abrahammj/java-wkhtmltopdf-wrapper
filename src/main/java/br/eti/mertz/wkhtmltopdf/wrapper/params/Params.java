package br.eti.mertz.wkhtmltopdf.wrapper.params;

import java.util.ArrayList;
import java.util.List;

public class Params {

    private List<Param> params;

    public Params() {
        this.params = new ArrayList<Param>();
    }

    public void add(Param param) {
        params.add(param);
    }

    public void add(Param... params) {
        for (Param param : params) {
            add(param);
        }
    }

    public List<String> getParamsAsStringList() {
        return getParamsAsStringListInternal(ResultSetType.ALL);
    }
    
    public List<String> getGlobalParamsAsStringList() {
        return getParamsAsStringListInternal(ResultSetType.GLOBAL);
    }
    
    public List<String> getPageParamsAsStringList() {
        return getParamsAsStringListInternal(ResultSetType.PAGE);
    }
    
    public List<String> getParamsAsStringListInternal(ResultSetType type) {
    	List<String> commandLine = new ArrayList<String>();

        for (Param p : params) {
        	
        	if (!type.isPermitted(p)) {
        		continue;
        	}
        	
            commandLine.add(p.getKey());

            String value = p.getValue();

            if (value != null) {
                commandLine.add(p.getValue());
            }
        }

        return commandLine;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Param param : params) {
            sb.append(param);
        }
        return sb.toString();
    }

    enum ResultSetType {
    	ALL,
    	GLOBAL,
    	PAGE;
    	
    	public boolean isPermitted(Param param) {
    		switch(this) {
    		case ALL:
    			return true;
    		case GLOBAL:
    			return param.isGlobal();
    		case PAGE:
    			return !param.isGlobal();
    		default:
    			throw new IllegalArgumentException("Unexpected type");
    		}
    	}
    }
    
}
