//
//  BasicGraphTest.swift
//  graphrs
//
//  Created by Marcus Sellmann on 31.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import XCTest
@testable import graphrs

class BasicGraphTest: XCTestCase {
    var graph: Graph?
    
    override func setUp() {
        super.setUp()
        
        graph = Graph()
        
        let v1 = graph!.addVertex(nil)
        let v2 = graph!.addVertex(nil)
        let v3 = graph!.addVertex(nil)
        let v4 = graph!.addVertex(nil)
        
        do {
            let e1: Edge? = try graph!.addEdge(v1.id, sideBId: v2.id, direction: .Forward, data: nil)
            let e2: Edge? = try graph!.addEdge(v1.id, sideBId: v3.id, direction: .Bi, data: nil)
            let e3: Edge? = try graph!.addEdge(v3.id, sideBId: v4.id, direction: .Forward, data: nil)
            let e4: Edge? = try graph!.addEdge(v3.id, sideBId: v1.id, direction: .Backward, data: nil)
            
            v1.addEdge(e1!)
            v1.addEdge(e2!)
            
            v2.addEdge(e1!)
            
            v3.addEdge(e2!)
            v3.addEdge(e3!)
            v3.addEdge(e4!)
            
            v4.addEdge(e3!)
        } catch {
            exit(1)
        }
    }
    
    override func tearDown() {
        graph = nil
        super.tearDown()
    }
    
    func testSimpleGraph() {
        XCTAssert(true)
    }
}
