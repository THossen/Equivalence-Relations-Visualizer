import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class EquivalencePanel extends JPanel {
    private ArrayList<String> elements;
    private ArrayList<Pair<String, String>> relations;
    private ArrayList<Pair<String, String>> transitiveRelations;


    public EquivalencePanel() {
        elements = new ArrayList<>();
        relations = new ArrayList<>();
        transitiveRelations = new ArrayList<>();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }


    public void addRelation(String a, String b) {
        if (!elements.contains(a)) {
            elements.add(a);
        }


        if (!elements.contains(b)) {
            elements.add(b);
        }


        relations.add(new Pair<>(a, b));
        checkAndAddTransitive(a, b);
    }


    private void checkAndAddTransitive(String a, String b) {
        for (Pair<String, String> relation : relations) {
            if (relation.getFirst().equals(b) && !relations.contains(new Pair<>(a, relation.getSecond()))) {
                transitiveRelations.add(new Pair<>(a, relation.getSecond()));
            } else if (relation.getSecond().equals(a) && !relations.contains(new Pair<>(relation.getFirst(), b))) {
                transitiveRelations.add(new Pair<>(relation.getFirst(), b));
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        int width = getWidth();
        int height = getHeight();


        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 50;


        int n = elements.size();
        double angle = 2 * Math.PI / n;


        for (int i = 0; i < n; i++) {
            int x = centerX + (int) (radius * Math.cos(i * angle));
            int y = centerY + (int) (radius * Math.sin(i * angle));


            g.setColor(Color.BLACK);
            g.fillOval(x - 5, y - 5, 10, 10);


            int labelOffset = 20;
            int labelX = centerX + (int) ((radius + labelOffset) * Math.cos(i * angle)) - 5;
            int labelY = centerY + (int) ((radius + labelOffset) * Math.sin(i * angle)) + 5;
            g.drawString(elements.get(i), labelX, labelY);
        }


        for (Pair<String, String> relation : relations) {
            int indexA = elements.indexOf(relation.getFirst());
            int indexB = elements.indexOf(relation.getSecond());


            int x1 = centerX + (int) (radius * Math.cos(indexA * angle));
            int y1 = centerY + (int) (radius * Math.sin(indexA * angle));
            int x2 = centerX + (int) (radius * Math.cos(indexB * angle));
            int y2 = centerY + (int) (radius * Math.sin(indexB * angle));


            g.setColor(Color.BLACK);


            if (indexA == indexB) {
                int circleRadius = 15;
                g.drawOval(x1 - circleRadius, y1 - circleRadius, 2 * circleRadius, 2 * circleRadius);
            } else {
                g.drawLine(x1, y1, x2, y2);


                double arrowHeadLength = 10;
                double arrowHeadWidth = 10;
                double angleLine = Math.atan2(y2 - y1, x2 - x1);
                double angle1 = angleLine - Math.PI / 6;
                double angle2 = angleLine + Math.PI / 6;
                int x2Adjusted = x2 - (int) ((5 + arrowHeadLength) * Math.cos(angleLine));
                int y2Adjusted = y2 - (int) ((5 + arrowHeadLength) * Math.sin(angleLine));


                int[] xPoints = { x2Adjusted, x2Adjusted - (int) (arrowHeadWidth * Math.cos(angle1)),
                        x2Adjusted - (int) (arrowHeadWidth * Math.cos(angle2)) };
                int[] yPoints = { y2Adjusted, y2Adjusted - (int) (arrowHeadWidth * Math.sin(angle1)),
                        y2Adjusted - (int) (arrowHeadWidth * Math.sin(angle2)) };
                g.fillPolygon(xPoints, yPoints, 3);
            }
        }


        Graphics2D g2 = (Graphics2D) g;
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(
                new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 4.0f }, 0.0f));


        for (Pair<String, String> transitiveRelation : transitiveRelations) {
            int indexA = elements.indexOf(transitiveRelation.getFirst());
            int indexB = elements.indexOf(transitiveRelation.getSecond());


            int x1 = centerX + (int) (radius * Math.cos(indexA * angle));
            int y1 = centerY + (int) (radius * Math.sin(indexA * angle));
            int x2 = centerX + (int) (radius * Math.cos(indexB * angle));
            int y2 = centerY + (int) (radius * Math.sin(indexB * angle));


            g.setColor(Color.BLACK);
            g2.drawLine(x1, y1, x2, y2);
        }


        g2.setStroke(originalStroke);
    }
}
