package org.alfresco.devops.services;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.alfresco.devops.util.Constants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ResourcesResolver {



	private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	public Set<String> resolveResourcesFromAMP(String path) throws IOException {
		final Resource[] resources = resourcePatternResolver.getResources(path);
		Set<String> res = new HashSet<String>();
		for (final Resource resource : resources) {
			final URL url = resource.getURL();
			int index = getIndex(url.toString());
			if(index>=0){
				String internalFilePath = url.toString().substring(index);
				res.add(internalFilePath);
			}
		}
		return res;
	}


	public boolean existsResource(String path) throws IOException {
		final Resource resource = resourcePatternResolver.getResource(path);
		if(resource!=null && resource.exists())
			return true;
		else{
			return false;
		}
	}

	private int getIndex(String url){
		if(url.indexOf(Constants.MODULE_PATH)>0){
			return url.indexOf(Constants.MODULE_PATH);
		}
		else if(url.indexOf(Constants.EXTENSION_PATH)>0){
			return url.indexOf(Constants.EXTENSION_PATH);
		}
		else return -1;
	}


}
