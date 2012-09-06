package com.xenoage.utils.math.geom;

import com.xenoage.utils.pdlib.Vector;


/**
 * Shape that consists of other shapes.
 *
 * @author Andreas Wenger
 */
public final class CompositeShape
{

	private Vector<Shape> shapes;


	public CompositeShape(Vector<Shape> shapes)
	{
		this.shapes = shapes;
	}


	/**
	 * Returns true, if the given point is contained
	 * within this shape.
	 */
	public boolean contains(Point2f point)
	{
		for (Shape shape : shapes) {
			if (shape.contains(point))
				return true;
		}
		return false;
	}

}
