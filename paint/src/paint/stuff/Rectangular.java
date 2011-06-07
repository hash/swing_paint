/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.stuff;

/**
 *
 * @author hash
 */
public abstract class Rectangular extends ThingToPaint {

    public void setActualBoundry() {
        if (mousex < Orx || mousey < Ory) {
            if (mousex < Orx) {
                OrWidth = Orx - mousex;
                drawX = Orx - OrWidth;
            } else {
                drawX = Orx;
                OrWidth = mousex - Orx;
            }
            if (mousey < Ory) {
                OrHeight = Ory - mousey;
                drawY = Ory - OrHeight;
            } else {
                drawY = Ory;
                OrHeight = mousey - Ory;
            }
        } else {
            drawX = Orx;
            drawY = Ory;
            OrWidth = mousex - Orx;
            OrHeight = mousey - Ory;
        }
    }
}
