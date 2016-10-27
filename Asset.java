package archbattlelord;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Asset {
    protected HashMap <String, String> attributes;
    protected ArrayList <Asset> children;
    protected String name;
    
    public Asset() {
        this.children = new ArrayList<>();
        this.attributes = new HashMap<>();
        
        this.name = "Unnamed Asset";
    }
    
    public ArrayList getChildren() {
        return children;
    }
    
    public HashMap getAttributes() {
        return attributes;
    }
    
    public void addAttributes(String en, String attribute) {
        attributes.put(en, attribute);
    }
   
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
