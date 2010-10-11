/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/

package automenta.netention.graph;

import com.syncleus.dann.graph.ImmutableDirectedEdge;

public class ValueEdge<N,V> extends ImmutableDirectedEdge<N> {

    public final V value;

    public ValueEdge(V value, N source, N target) {
        super(source, target);
        this.value = value;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + value.hashCode();
    }

    
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return value.equals(((ValueEdge) obj).value);
        }
        return false;
    }

    public V getValue() {
        return value;
    }

    
}
