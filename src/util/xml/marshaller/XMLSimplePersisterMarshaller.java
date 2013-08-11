package util.xml.marshaller;


import org.simpleframework.xml.core.Persister;

import java.io.File;

class XMLSimplePersisterMarshaller implements XMLMarshaller {
	
	private File file;
    private Persister persister = new Persister();
    Class<?> aClass;

	public XMLSimplePersisterMarshaller(String canonicalXMLFileName, Class<?> clazz) {
          file  =  new File(canonicalXMLFileName);
          aClass = clazz;
	}

	@Override
	public void marshal(Object entity) {
        try {
            persister.write(entity, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public Object unmarshal() {
        Object res = null;
        try {
            res = persister.read(aClass, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
