package axoloti.inlets;

import axoloti.mvc.AbstractView;
import axoloti.objectviews.IAxoObjectInstanceView;
import java.awt.Point;

public interface IInletInstanceView extends AbstractView {

    public void PostConstructor();

    public InletInstance getModel();

    @Override
    public InletInstanceController getController();

    public void setHighlighted(boolean highlighted);

    public void repaint();

    public Point getJackLocInCanvas();

    public IAxoObjectInstanceView getObjectInstanceView();

    public void setAlignmentX(float alignmentX);

    public void setAlignmentY(float alignmentY);
}
