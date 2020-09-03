package services.resourceService;

import base.ResourceService;
import services.resourceService.resourceServer.ResourceServer;
import services.resourceService.resourceServer.ResourceServerController;
import services.resourceService.resourceServer.ResourceServerControllerMBean;
import resources.TestResource;
import services.resourceService.readXMLFile.sax.ReadXMLFileSAX;

import javax.management.*;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            result = ReadXMLFileSAX.readXML(stringPath);
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
