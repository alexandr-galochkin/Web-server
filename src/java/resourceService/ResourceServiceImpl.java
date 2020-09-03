package resourceService;

import base.ResourceService;
import resourceService.resourceServer.ResourceServer;
import resourceService.resourceServer.ResourceServerController;
import resourceService.resourceServer.ResourceServerControllerMBean;
import resources.TestResource;

import javax.management.*;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

import static resourceService.readXMLFile.sax.ReadXMLFileSAX.readXML;

public class ResourceServiceImpl implements ResourceService {
    private String root;
    private ResourceFactory resourceFactory;

    public ResourceServiceImpl(String root){
        this.resourceFactory = ResourceFactory.instance();
        this.root = root;
    }

    @Override
    public RandomAccessFile getFile(String stringPath) {
        Path path = Paths.get(root+stringPath);
        return resourceFactory.getFile(path);
    }

    @Override
    public byte[] readBytes(RandomAccessFile file) {
        return resourceFactory.readBytes(file);
    }

    @Override
    public String readString(RandomAccessFile file) {
        return resourceFactory.readString(file);
    }

    @Override
    public Object createObjectFromFile(String stringPath) {
        Object result = null;
        try{
            result = readXML(stringPath);
            ResourceServer resourceServer = new ResourceServer((TestResource)result);
            ResourceServerControllerMBean serverStatistics = new ResourceServerController(resourceServer);
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("Admin:type=ResourceServerController");
            mbs.registerMBean(serverStatistics, name);}
        catch (MalformedObjectNameException | MBeanRegistrationException | InstanceAlreadyExistsException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }
        return result;
    }
}
