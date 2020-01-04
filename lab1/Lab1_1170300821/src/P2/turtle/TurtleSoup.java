/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;

import java.math.BigDecimal;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides
	 *            number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		if (sides <= 2)
			return 0;
		else {
			double result = (180d * (sides - 2)) / sides;
			return result;
		}
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle
	 *            size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		double exterior = 180d - angle;
		int sides;
		double temp = (360d / exterior);
		sides = (int) Math.round(temp);
		return sides;
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sides
	 *            number of sides of the polygon to draw
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		double angle = 180d - calculateRegularPolygonAngle(sides);
		turtle.forward(sideLength);
		for (int i = 0; i < sides - 1; i++) {
			turtle.turn(angle);
			turtle.forward(sideLength);
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the Bearing towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentBearing. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentBearing
	 *            current direction as clockwise from north
	 * @param currentX
	 *            current location x-coordinate
	 * @param currentY
	 *            current location y-coordinate
	 * @param targetX
	 *            target point x-coordinate
	 * @param targetY
	 *            target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
			int targetY) {
		int Dx = targetX - currentX, Dy = targetY - currentY;
		double sides = 0;

		if (Dx == 0 && Dy > 0) {
			if (currentBearing != 0d)
				sides = 360d - currentBearing;
			else
				sides = 0d;
		} else if (Dx == 0 && Dy < 0) {
			if (currentBearing <= 180d)
				sides = 180d - currentBearing;
			else
				sides = 360d - currentBearing + 180d;
		} else if (Dx > 0 && Dy == 0) {
			if (currentBearing <= 90d)
				sides = 90d - currentBearing;
			else
				sides = 360d - currentBearing + 90d;
		} else if (Dx < 0 && Dy == 0) {
			if (currentBearing <= 270d)
				sides = 270d - currentBearing;
			else
				sides = 360d - currentBearing + 270d;
		} else {
			double D_angle = Math.toDegrees(Math.atan(Dx / (Dy * 1.0)));
			if (Dx > 0 && Dy > 0)
				D_angle = D_angle;
			else if (Dx > 0 && Dy < 0)
				D_angle = 180d + D_angle;
			else if (Dx < 0 && Dy > 0)
				D_angle = 360d + D_angle;
			else if (Dx < 0 && Dy < 0)
				D_angle = 180d + D_angle;

			if (currentBearing <= D_angle)
				sides = D_angle - currentBearing;
			else
				sides = 360d - D_angle + currentBearing;
		}

		return sides;
	}

	/**
	 * Given a sequence of points, calculate the Bearing adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateBearingToPoint() to implement this function.
	 * 
	 * @param xCoords
	 *            list of x-coordinates (must be same length as yCoords)
	 * @param yCoords
	 *            list of y-coordinates (must be same length as xCoords)
	 * @return list of Bearing adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
		List<Double> result = new ArrayList<>();
		int n = xCoords.size();
		int currentX = xCoords.get(0), currentY = yCoords.get(0);
		int targetX = xCoords.get(1), targetY = yCoords.get(1);
		result.add(calculateBearingToPoint(0, currentX, currentY, targetX, targetY));

		for (int i = 1; i < n - 1; i++) {
			int current_X = xCoords.get(i), current_Y = yCoords.get(i);
			int target_X = xCoords.get(i + 1), target_Y = yCoords.get(i + 1);
			double currentBearing = result.get(i - 1);
			double re = calculateBearingToPoint(currentBearing, current_X, current_Y, target_X, target_Y);
			result.add(re % 360);
		}

		return result;
	}

	/**
	 * Given a set of points, compute the convex hull, the smallest convex set that
	 * contains all the points in a set of input points. The gift-wrapping algorithm
	 * is one simple approach to this problem, and there are other algorithms too.
	 * 
	 * @param points
	 *            a set of points with xCoords and yCoords. It might be empty,
	 *            contain only 1 point, two points or more.
	 * @return minimal subset of the input points that form the vertices of the
	 *         perimeter of the convex hull
	 */
	public static Set<Point> convexHull(Set<Point> points) {
		Set<Point> ansSet = new HashSet<>();
		if (points.size() <= 3)
			return points;
		
		Double MLD = Double.MAX_VALUE;
		Point left = new Point(0, 0);
		for (Point ptPoint : points) {
			if (ptPoint.x() < MLD || (ptPoint.x() == MLD && ptPoint.y() < left.y())) {
				MLD = ptPoint.x();
				left = ptPoint;
			}
		}
		
		ansSet.add(left);
		Point pt2 = new Point(left.x(), left.y());
		Point pt1 = new Point(left.x(), left.y() - 0.000001);
		while (true) {
			Double max = -Double.MAX_VALUE;
			Point next = null;
			for (Point pt : points) {
				Double cosDouble = getCosOf2Vector(pt1, pt2, pt2, pt);
				if (cosDouble > max || cosDouble == max
						&& caculateDistance(pt, pt2) > caculateDistance(next, pt2)) {
					max = cosDouble;
					next = pt;
				}
			}
			if (next == left)
				break;

			ansSet.add(next);
			pt1 = pt2;  pt2 = next;
		}
		return ansSet;
	}

	public static final Double eps = 1e-8;

	public static Double getCosOf2Vector(Point a1, Point a2, Point b1, Point b2) {
		Point vec1 = new Point(a2.x() - a1.x(), a2.y() - a1.y());
		Point vec2 = new Point(b2.x() - b1.x(), b2.y() - b1.y());
		Double absVec1 = Math.sqrt(vec1.x() * vec1.x() + vec1.y() * vec1.y());
		Double absVec2 = Math.sqrt(vec2.x() * vec2.x() + vec2.y() * vec2.y());
		return (vec1.x() * vec2.x() + vec1.y() * vec2.y()) / (absVec1 * absVec2);
	}

	public static Double caculateDistance(Point p, Point a) {
		return (p.x() - a.x()) * (p.x() - a.x()) + (p.y() - a.y()) * (p.y() - a.y());
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle
	 *            the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {
		PenColor color = PenColor.YELLOW;
		turtle.color(color);
		turtle.turn(180d - 18d);
		for (int i = 0; i < 5; i++) {
			turtle.forward(60);
			turtle.turn(360d - 72d);
			turtle.forward(60);
			turtle.turn(180d - 36d);
		}

		PenColor color1 = PenColor.RED;
		turtle.color(color1);
		turtle.turn(360d - 36d);
		for (int i = 0; i < 5; i++) {
			turtle.forward(97);
			turtle.turn(72);
		}

		PenColor color2 = PenColor.BLUE;
		turtle.color(color2);
		turtle.turn(36d);
		for (int i = 0; i < 5; i++) {
			turtle.forward(160);
			turtle.turn(144d);
		}
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		// drawSquare(turtle, 40);
		drawPersonalArt(turtle);
		// draw the window
		turtle.draw();
	}

}
