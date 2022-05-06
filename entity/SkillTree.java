package entity;

import java.util.ArrayList;

public class SkillTree {
    SkillTreeNode[] roots;

    public SkillTree(SkillTreeNode[] roots) {
        this.roots = roots.clone();
    }
    public SkillTreeNode[] getActivatable() {
        ArrayList<SkillTreeNode> activatable = new ArrayList<SkillTreeNode>();
        for (SkillTreeNode root : roots) {
            if(!root.isActive()) {
                activatable.add(root);
            }
            else {
                getActivatable(root, activatable);
            }
        }
        return activatable.toArray(new SkillTreeNode[activatable.size()]);
    }
    private void getActivatable(SkillTreeNode root, ArrayList<SkillTreeNode> activatable) {
        if(!root.isActive()) {
            activatable.add(root);
        }
        else {
            for (SkillTreeNode node : root.getNextNodes()) {
                getActivatable(node, activatable);
            }
        }
    }
    
}
