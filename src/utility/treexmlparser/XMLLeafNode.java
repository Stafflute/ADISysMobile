package utility.treexmlparser;

public class XMLLeafNode extends XMLNode
{
    public StringBuilder value;

    public XMLLeafNode(XMLNode parent, String value)
    {
        super(parent);
        
        this.value = new StringBuilder(value);
    }

    public String generateXML()
    {
        String raw = value.toString().trim();
        String[] rows = raw.split("\\r?\\n");
        String result = "";
        if(rows.length != 1) {
            for(int i = 0; i < rows.length; i++) {
                //if(i != 0) {
                    result += "\n" + indent();
                //}
                result += rows[i];
            }
            XMLNode.level--;
            result += "\n" + indent();
        } else {
            result += rows[0];
            XMLNode.level--;
        }
        //return value.toString();
        return  result;
    }
}
