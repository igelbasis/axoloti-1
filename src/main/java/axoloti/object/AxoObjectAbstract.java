/**
 * Copyright (C) 2013, 2014, 2015 Johannes Taelman
 *
 * This file is part of Axoloti.
 *
 * Axoloti is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Axoloti is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Axoloti. If not, see <http://www.gnu.org/licenses/>.
 */
package axoloti.object;

import axoloti.Modulator;
import axoloti.inlets.Inlet;
import axoloti.mvc.AbstractController;
import axoloti.mvc.AbstractDocumentRoot;
import axoloti.mvc.AbstractModel;
import axoloti.mvc.array.ArrayModel;
import axoloti.outlets.Outlet;
import java.util.HashSet;
import java.util.Set;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;
import org.simpleframework.xml.core.Persist;

/**
 *
 * @author Johannes Taelman
 */
@Root(name = "objdef")
public abstract class AxoObjectAbstract extends AbstractModel implements Comparable, Cloneable, IAxoObject {

    @Attribute
    public String id;

    @Attribute(required = false)
    String uuid;

    @Deprecated
    @Attribute(required = false)
    String sha;

    @Deprecated
    @ElementListUnion({
        @ElementList(entry = "upgradeSha", type = String.class, inline = true, required = false),})
    HashSet<String> upgradeSha;

    @Element(name = "sDescription", required = false)
    String sDescription;

    public String shortId;

    public boolean createdFromRelativePath = false;

    @Element(name = "author", required = false)
    private String sAuthor;
    @Element(name = "license", required = false)
    private String sLicense;
    private String sPath;

    @Commit
    public void Commit() {
        // called after deserialializtion
        this.sha = null;
        this.upgradeSha = null;
    }

    @Persist
    public void Persist() {
        // called prior to serialization
        this.sha = null;
        this.upgradeSha = null;
    }

    public AxoObjectAbstract() {
        this.sha = null;
        this.upgradeSha = null;
    }

    public AxoObjectAbstract(String id, String sDescription) {
        this.sDescription = sDescription;
        this.id = id;
        this.sha = null;
        this.upgradeSha = null;
    }

    public abstract ArrayModel<Inlet> getInlets();

    public abstract ArrayModel<Outlet> getOutlets();

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int compareTo(Object t) {
        String tn = ((AxoObject) t).id;
        if (id.startsWith(tn)) {
            return 1;
        }
        if (tn.startsWith(id)) {
            return -1;
        }
        return id.compareTo(tn);
    }

    public boolean providesModulationSource() {
        return false;
    }

    public String getCName() {
        return "noname";
    }

    public String getUUID() {
        if (uuid == null) {
            uuid = GenerateUUID();
        }
        return uuid;
    }

    public HashSet<String> GetIncludes() {
        return null;
    }

    public void SetIncludes(HashSet<String> includes) {
    }

    public Set<String> GetDepends() {
        return null;
    }

    public Set<String> GetModules() {
        return null;
    }

    public String getDefaultInstanceName() {
        if (shortId == null) {
            return "obj";
        }
        int i = shortId.indexOf(' ');
        if (i > 0) {
            return shortId.substring(0, i);
        }
        return shortId;
    }

    public Modulator[] getModulators() {
        return null;
    }

    public abstract String GenerateUUID();

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    
/* MVC clean methods below... */    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        String oldvalue = this.id;
        this.id = id;
        firePropertyChange(
                ObjectController.OBJ_ID,
                oldvalue, id);
    }

    public String getDescription() {
        return sDescription;
    }

    public void setDescription(String sDescription) {
        String oldvalue = this.sDescription;
        this.sDescription = sDescription;
        firePropertyChange(
                ObjectController.OBJ_DESCRIPTION,
                oldvalue, sDescription);
    }

    public String getLicense() {
        return sLicense;
    }

    public void setLicense(String sLicense) {
        String oldvalue = this.sLicense;
        this.sLicense = sLicense;
        firePropertyChange(
                ObjectController.OBJ_LICENSE,
                oldvalue, sLicense);
    }

    public String getPath() {
        return sPath;
    }

    public void setPath(String sPath) {
        String oldvalue = this.sPath;
        this.sPath = sPath;
        firePropertyChange(
                ObjectController.OBJ_PATH,
                oldvalue, sPath);
    }

    public String getAuthor() {
        return sAuthor;
    }

    public void setAuthor(String sAuthor) {
        String oldvalue = this.sAuthor;
        this.sAuthor = sAuthor;
        firePropertyChange(
                ObjectController.OBJ_AUTHOR,
                oldvalue, sPath);
    }

    // Let's violate the MVC pattern for now and use a singleton controller for this model
    // how undo/redo must be handled when open documents contain instances is yet unclear...
    private ObjectController controller;

    public ObjectController createController(AbstractDocumentRoot documentRoot, AbstractController parent) {
        if (controller == null) {
            controller = new ObjectController(this, documentRoot);
        }
        return controller;
    }

    @Override
    public boolean isCreatedFromRelativePath() {
        return createdFromRelativePath;
    }

}
