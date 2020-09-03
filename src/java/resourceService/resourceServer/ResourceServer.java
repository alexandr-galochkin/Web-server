package resourceService.resourceServer;

import resources.TestResource;

public class ResourceServer {
    private TestResource testResource;

    public ResourceServer(TestResource testResource){
        this.testResource = testResource;
    }

    public ResourceServer(String pathToResource){
        this.testResource = testResource;
    }

    public String getname(){
        return testResource.getName();
    }

    public int getage(){
        return testResource.getAge();
    }
}
