package eCommerce.AbstractComponents;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHandler {
	Properties prop;
	public JSONHandler(Properties prop) {
		this.prop = prop;
	}

	public List<HashMap<Object, Object>> getJSONDataToMap(String filePath) throws StreamReadException, DatabindException, IOException {
		
		Path path = Paths.get(System.getProperty("user.dir")+prop.getProperty(filePath));
		String content = Files.readString(path);
		ObjectMapper om = new ObjectMapper();
		List<HashMap<Object, Object>> result =
		        om.readValue(content, new TypeReference<List<HashMap<Object, Object>>>() {
				});
		return result;
	}
}
