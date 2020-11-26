/**
 *  This file is part of Scrabble DictionaryFrame.
 *
 *  Scrabble DictionaryFrame is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Scrabble DictionaryFrame is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Scrabble DictionaryFrame.  If not, see <http://www.gnu.org/licenses/>.
 */
package zen.scrabbledict.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

/**
 * This instantiates an object that allows the developer to set an explicit
 * traversal policy.
 * @author flszen
 */
public class ExplicitFocusTraversal extends FocusTraversalPolicy {

    private Vector<Component> components;
    private Component defaultComponent;
    
    /**
     * Creates a new ExplicitFocusTraversal with no inital components.
     */
    public ExplicitFocusTraversal() {
        components = new Vector<Component>();
    }

    /**
     * Creates a new ExplicitFocusTaversal with a set of initial components.
     * @param components The initial components to traverse.
     */
    public ExplicitFocusTraversal(Vector<Component> components) {
        this.components = components;
    }

    /**
     * Adds a component to this traversal policy.
     * @param aComponent The componet to add.
     */
    public void add(Component aComponent) {
        components.add(aComponent);
    }
    
    /**
     * Sets teh defualt component.
     * @param aComponent
     */
    public void setDefaultComponent(Component aComponent) {
        defaultComponent = aComponent;
    }
    
    @Override
    public Component getComponentAfter(Container aContainer, Component aComponent) {
        Component component;
        int index = components.indexOf(aComponent);
        
        if (index == components.size() - 1) {
            component = components.firstElement();
        } else {
            component =  components.get(index + 1);
        }
        
        // Go to the next component if this one is disabled.
        if (component.isEnabled()) {
            return component;
        } else {
            return getComponentAfter(aContainer, component);
        }
    }

    @Override
    public Component getComponentBefore(Container aContainer, Component aComponent) {
        Component component;
        int index = components.indexOf(aComponent);
        
        if (index == 0) {
            component = components.lastElement();
        } else {
            component = components.get(index - 1);
        }
        
        // Go to the previous component if this one is disabled.
        if (component.isEnabled()) {
            return component;
        } else {
            return getComponentBefore(aContainer, component);
        }
    }

    @Override
    public Component getFirstComponent(Container aContainer) {
        return components.firstElement();
    }

    @Override
    public Component getLastComponent(Container aContainer) {
        return components.lastElement();
    }

    @Override
    public Component getDefaultComponent(Container aContainer) {
        if (defaultComponent == null) {
            return components.firstElement();
        } else {
            return defaultComponent;
        }
    }
}
