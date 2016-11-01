package archbattlelord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Asset {
    protected HashMap <String, String> attributes;
    protected ArrayList <Asset> children;
    protected String name;
    
    
    public Asset() {
        this.children = new ArrayList<>();
        this.attributes = new HashMap<>();
        
        this.name = "Unnamed Asset";
    }
    
    public Asset search (int depth, String index) {
        List <Asset> search_list = new ArrayList<>(children);
       
        while (depth > 1) {
            List <Asset> tmp_list = new ArrayList<>();
            for (Asset node : search_list) {
                tmp_list.addAll(node.children);
            }
            
            search_list = tmp_list;
            depth = (depth - 1);
            System.out.println("DEPTH " + depth);
        }
        
        Asset ans = null;
        for (Asset node:search_list) {
            System.out.println("Echo- " + node.attributes.get("index") + "  KEY- " + index);
            
            if (node.attributes.containsKey("index") && node.attributes.get("index").equals(index)) {
                ans = node;
                break;
            }
        }
        return ans;
    }
        
    public ArrayList getChildren() {
        return children;
    }
    
    public HashMap getAttributes(String str) {
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
