/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 *
 * @author rrufai
 */
public class ExitAction extends AbstractAction {

    public ExitAction() {
        super();
    }

    public ExitAction(String name, Icon icon) {
        super(name, icon);
    }

    public ExitAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
