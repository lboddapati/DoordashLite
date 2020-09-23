package com.interview.doordashlite.ui

data class Node(
    val value: Int,
    var nextNode: Node? = null
)

class LinkedList {
    // insert a node
    // search a node
    // delete a node
    // iterate through the list

    var head: Node? = null
    var tail: Node? = null

    fun insert(value: Int) {
        val node = Node(value)
        if (head == null) {
            head = node
        }
        if (tail != null) {
            tail?.nextNode = node
        }
        tail = node
    }

    fun delete(value: Int): Boolean {
//        val prevNode
        // currentNode
        // TODO: empty checks
        // 1. delete the head node
        if (head?.value == value) {
            removeHead()
        } else if (tail?.value == value) {
            removeTail()
        } else {
            var prevNode = head
            var currNode = head?.nextNode
            while (currNode != null) {
                if (currNode.value == value) {
                    prevNode?.nextNode = currNode.nextNode
                    currNode.nextNode = null
                    break
                }
                prevNode = currNode
                currNode = currNode.nextNode
            }
        }
        // 2. remove from the middle
        // 3. remove the tail


    }

    fun find(value: Int): Node? {
//        if (head == null) return null
        var node = head
        while(node != null) {
            if (node.value == value) {
                return node
            }
            node = node.nextNode
        }
        return null
    }
}