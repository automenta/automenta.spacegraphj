package automenta.netention.demo;

import javax.swing.JPanel;

public interface Demo {

    public String getName();

    public String getDescription();

    public abstract JPanel newPanel();
}
