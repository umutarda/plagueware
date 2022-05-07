package entity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        if(root == null) {
            return;
        }
        if(!root.isActive()) {
            activatable.add(root);
        }
        else {
            for (SkillTreeNode node : root.getNextNodes()) {
                getActivatable(node, activatable);
            }
        }
    }
    public static void main(String[] args) {
        SkillTreeNode[] roots = {new SkillTreeNode() {

            @Override
            protected void activateEvent() {
                System.out.println("Active");
            }
            @Override
            public int getCost() {
                return 0;
            }
            @Override
            public String toString() {
                return "skill1";
            }
            
        }, new SkillTreeNode() {

            @Override
            protected void activateEvent() {
                System.out.println("Active2");
            }
            @Override
            public int getCost() {
                return 0;
            }
            @Override
            public String toString() {
                return "skill2";
            }
            
        }};
        roots[0].addNextNode(new SkillTreeNode() {

            @Override
            protected void activateEvent() {
                System.out.println("Active3");
                
            }

            @Override
            public int getCost() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String toString() {
                // TODO Auto-generated method stub
                return "skill3";
            }
            
        });
        SkillTree tree = new SkillTree(roots);
        System.out.println(Arrays.toString(tree.getActivatable()));
        tree.getActivatable()[0].activate();
        System.out.println(Arrays.toString(tree.getActivatable()));
    }
    
}
