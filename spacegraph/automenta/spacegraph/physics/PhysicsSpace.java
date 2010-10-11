/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacegraph.physics;

import automenta.spacegraph.shape.Rect;
import java.util.HashMap;
import java.util.Map;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.Shape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.XForm;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.PolygonDef;

/**
 *
 * @author seh
 */
public class PhysicsSpace extends World {

    public final Map<Rect, Body> bodies = new HashMap();

    public PhysicsSpace(AABB worldAABB, Vec2 gravity, boolean completeSimulation) {
        super(worldAABB, gravity, completeSimulation);
    }

    public static class RectBoxDef extends PolygonDef {

        private RectBoxDef(Rect r) {
        }
    }

    public class BoxDef extends PolygonDef {

        public Vec2 extents;
        Vec2 v2 = new Vec2();
        private final Rect r;
        
        public BoxDef(Rect r) {
            super();
            
            this.r = r;
            
            update();
        }
        
        public void update() {
            v2.set(r.getCenter().x(), r.getCenter().y());
            setAsBox(r.getScale().x(), r.getScale().y(), v2,  r.getRotation().get(0));
        }
    }

    public Body addRect(Rect r) {

        BodyDef bd = new BodyDef();
        
        Body body = this.createBody(bd);
        body.createShape(new BoxDef(r));
        body.setMassFromShapes();
                
        bodies.put(r, body);               

        return body;
    }

    public boolean removeRect(Rect r) {
        Body body = bodies.get(r);
        if (body == null) {
            return false;
        } else {
            bodies.remove(r);
            return true;
        }
    }

    @Override
    public void drawShape(Shape shape, XForm xf, Color3f color, boolean core) {
        //super.drawShape(shape, xf, color, core);
    }
}
