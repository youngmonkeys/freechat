//
//  Mvc.swift
//  hello-swift
//
//  Created by Dung Ta Van on 10/30/18.
//  Copyright © 2018 Young Monkeys. All rights reserved.
//

import Foundation

public class Mvc {
    private var model = Model()
    private var controllers : [String: Controller]
    private static let INSTANCE = Mvc()
    
    public static func getInstance() -> Mvc {
        return INSTANCE;
    }
    
    private init() {
        controllers = [String: Controller]()
    }
    
    public func getModel() -> Model {
        return model
    }
    
    public func addController(name: String, controller: Controller) {
        controllers[name] = controller
    }
    
    public func getController(name: String) -> Controller {
        let controller = controllers[name]
        return controller!
    }
}

public protocol View {
    func update(component: String, data: Any) -> Void
}

public class Controller {
    private var views : [String: [String : View]]
    
    public init() {
        views = [String: [String : View]]()
    }
    
    public func addView(action: String, view: View) {
        addView(action: action, viewId: action, view: view)
    }
    
    public func addView(action: String, viewId: String, view: View) {
        var available = views[action]
        if(available == nil) {
            available = [String : View]()
        }
        available![viewId] = view
        views[action] = available
    }
    
    public func removeView(action : String, viewId: String) {
        var available = views[action]
        if(available != nil) {
            available!.removeValue(forKey: viewId)
        }
    }
    
    public func updateViews(action: String) {
        updateViews(action: action, data: 0)
    }
    
    public func updateViews(action: String, data: Any) {
        updateViews(action: action, component: action, data: data)
    }
    
    public func updateViews(action: String, component: String, data: Any) {
        let available = views[action]
        if(available != nil) {
            for (_, v) in available! {
                v.update(component: component, data: data)
            }
        }
    }
}

public class Model {
    private let dataByName = NSMutableDictionary()
    
    public func get(name: String) -> Any? {
        return dataByName[name]
    }
    
    public func set(name: String, value: Any) -> Void {
        dataByName[name] = value
    }
}
