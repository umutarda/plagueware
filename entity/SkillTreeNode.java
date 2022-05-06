package entity;

public abstract class SkillTreeNode {
    private SkillTreeNode[] nextNodes;
    private int nextNodeAmount;
    private boolean isActive;

    public SkillTreeNode() {
        nextNodes = new SkillTreeNode[2];
        isActive = false;
    }
    public void addNextNode(SkillTreeNode node) {
        if(nextNodeAmount == nextNodes.length) {
            SkillTreeNode[] newArr = new SkillTreeNode[nextNodes.length * 2];
            for (int i = 0; i < nextNodes.length; i++) {
                newArr[i] = nextNodes[i];
            }
            nextNodes = newArr;
        }
        nextNodes[nextNodeAmount] = node;
        nextNodeAmount++;
    }
    public SkillTreeNode[] getNextNodes() {
        return nextNodes.clone();
    }
    public boolean isActive() {
        return isActive;
    }
    public void activate() {
        if(!isActive) {
            activateEvent();
            isActive = true;
        }
    }
    protected abstract void activateEvent();
    public abstract String toString();
}
