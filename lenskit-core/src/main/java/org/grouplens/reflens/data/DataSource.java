/*
 * RefLens, a reference implementation of recommender algorithms.
 * Copyright 2010-2011 Regents of the University of Minnesota
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
/**
 * 
 */
package org.grouplens.reflens.data;

import java.io.Closeable;

import javax.annotation.PreDestroy;


/**
 * Represents a data source.  More properly, this represents a connection to a
 * data source, expected to exhibit transactional behavior in that the view of
 * the data will not change between when it is constructed and closed.  This
 * allows recommender-building code to take multiple passes over the data.
 * 
 * <p>RefLens code does not close data sources - that is the responsibility of
 * the client code.  This is in order to facilitate reuse of data sources within
 * operations.  See {@link DataSourceProvider} for further discussion of this,
 * particularly as it relates to dependency injection.
 * 
 * @see DataSourceProvider
 * 
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 */
public interface DataSource extends Closeable {
	/**
	 * Retrieve the users from the data source.
	 * @return a cursor iterating the user IDs.
	 */
	public LongCursor getUsers();
	
	/**
	 * Get the number of users in the system.  This should be the same number
	 * of users that will be returned by iterating {@link #getUsers()}.
	 * @return The number of users in the system.
	 */
	public int getUserCount();
	
	/**
	 * Retrieve the items from the data source.
	 * @return a cursor iterating the item IDs.
	 */
	public LongCursor getItems();
	
	/**
	 * Get the number of items in the system.  This should be the same number
	 * of items that will be returned by iterating {@link #getItems()}.
	 * @return The number of items in the system.
	 */
	public int getItemCount();
	
	/**
	 * Close the data source.  Any subsequent operations are invalid.
	 * 
	 * <p>Implementations are not required to enforce closure, but are permitted
	 * to throw {@link RuntimeException}s from any other method after
	 * <tt>close()</tt> has been called for no other reason that the source
	 * has been closed.
	 */
	@PreDestroy
	public void close();
}