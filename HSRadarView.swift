//
//  HSRadarView.swift
//  CloneData
//
//  Created by 何少博 on 2017/6/21.
//  Copyright © 2017年 Yanqh. All rights reserved.
//

import UIKit




class HSRadarView: UIView {
    fileprivate  var waveWidth:CGFloat = 40
    var waveCount: Int = 5 { didSet { setup() } }
    var waveColor: UIColor = UIColor.blue { didSet { setup() } }
    var waveMaxAlpha: CGFloat = 1.0 { didSet { setup() } }
    var animationDuration: Double = 6.6 { didSet { setup() } }
    var animationOptions: UIViewAnimationOptions = [.curveEaseOut] { didSet { setup() } }
    
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setup()
    }
    
    func setup() {
        for view in self.subviews {
            view.removeFromSuperview()
        }
        
        for _ in 0..<waveCount {
            let wave = HSWaveView(frame: CGRect(x: 0, y: 0, width: waveWidth, height: waveWidth))
            wave.center = self.center
            wave.borderColor = waveColor
            wave.alpha = waveMaxAlpha
            
            self.addSubview(wave)
        }
    }
    
    func startAnimation() {
        if self.subviews.count == 0 {
            setup()
        }
        let views = self.subviews
        let scale = self.bounds.width / waveWidth
        for (index, view) in views.enumerated() {
            
            let delay = animationDuration / Double(waveCount) * Double(index)
            UIView.animate(withDuration: animationDuration, delay: delay, options: [.repeat, animationOptions], animations: {
                view.transform = view.transform.scaledBy(x: scale, y: scale);
                view.alpha = 0.0
            }, completion: nil)
        }
    }
    func stopAnimation() -> () {
        for view in self.subviews {
            view.removeFromSuperview()
        }
    }
    
}

fileprivate class HSWaveView: UIView {
    
    var borderColor = UIColor.blue { didSet{ setNeedsDisplay() } }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = UIColor.clear
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        backgroundColor = UIColor.clear
    }
    
    override func draw(_ rect: CGRect) {
        
        let radius = rect.width/2
        let path = UIBezierPath(roundedRect: rect, cornerRadius: radius)
        path.lineWidth = 1.0
        
        let layer = CAShapeLayer()
        layer.path = path.cgPath;
        layer.strokeColor = borderColor.cgColor
        layer.fillColor = borderColor.cgColor//UIColor.clear.cgColor
        self.layer.addSublayer(layer)
        
    }
    
    
}



