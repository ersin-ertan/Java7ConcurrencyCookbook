package com.nullcognition.java7concurrencycookbook.chapter02;// Created by ersin on 16/05/15

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicRef <T,V,E>{
	// T - The type of the object holding the updatable field
	// V - The type of object referred to by this reference
	// E - The base class of elements held in this array

	AtomicBoolean ab;                   // A boolean value that may be updated atomically.

	AtomicInteger ai;                   // An int value that may be updated atomically.
	AtomicIntegerArray aia;             // An int array in which elements may be updated atomically.
	AtomicIntegerFieldUpdater<T> aifu;  // A reflection-based utility that enables atomic updates to designated volatile int fields of designated classes.

	AtomicLong al;                      // A long value that may be updated atomically.
	AtomicLongArray ala;                // A long array in which elements may be updated atomically.
	AtomicLongFieldUpdater<T> alfu;     // A reflection-based utility that enables atomic updates to designated volatile long fields of designated classes.

	AtomicReference<V> ar;              // An object reference that may be updated atomically.
	AtomicReferenceArray<E> ara;        // An array of object references in which elements may be updated atomically.
	AtomicReferenceFieldUpdater<T,V> arfu;// A reflection-based utility that enables atomic updates to designated volatile reference fields of designated classes.
	AtomicStampedReference<V> asr;      // An AtomicStampedReference maintains an object reference along with an integer "stamp", that can be updated atomically.
	AtomicMarkableReference<V> amr;     // An AtomicMarkableReference maintains an object reference along with a mark bit, that can be updated atomically.
}
