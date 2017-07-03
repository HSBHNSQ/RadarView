//
//  HSRadarView.m
//  CloneData
//
//  Created by 何少博 on 2017/7/1.
//  Copyright © 2017年 shaobo.He. All rights reserved.
//

#import "HSRadarView.h"


@interface HSWaveView : UIView

@property(nonatomic,strong)UIColor *borderColor;

@end

@implementation HSRadarView

-(instancetype)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        [self setDefaultValue];
        [self setup];
    }
    return self;
}

-(instancetype)initWithCoder:(NSCoder *)aDecoder{
    self = [super initWithCoder:aDecoder];
    if (self) {
        [self setDefaultValue];
        [self setup];
    }
    return self;
}


-(void)setWaveCount:(NSInteger)waveCount{
    _waveCount = waveCount;
    [self setup];
}
-(void)setWaveColor:(UIColor *)waveColor{
    _waveColor = waveColor;
    [self setup];
}
-(void)setWaveWidth:(CGFloat)waveWidth{
    _waveWidth = waveWidth;
    [self setup];
}
-(void)setWaveMaxAlpha:(CGFloat)waveMaxAlpha{
    _waveMaxAlpha = waveMaxAlpha;
    [self setup];
}
-(void)setAnimationDuration:(CGFloat)animationDuration{
    _animationDuration = animationDuration;
    [self setup];
}
-(void)setAnimationOptions:(UIViewAnimationOptions)animationOptions{
    _animationOptions = animationOptions;
    [self setup];
}

-(void)setDefaultValue{
    _waveCount = 5;
    _waveWidth = 40;
    _waveColor = [UIColor blueColor];
    _waveMaxAlpha = 1.0;
    _animationDuration = 6.6;
    _animationOptions = UIViewAnimationOptionCurveEaseOut;
}

-(void)setup{
    for (UIView * view in self.subviews) {
        [view removeFromSuperview];
    }
    
    for (int i = 0; i < _waveCount; i++) {
        HSWaveView * wave = [[HSWaveView alloc]initWithFrame:CGRectMake(0, 0, self.waveWidth, self.waveWidth)];
        wave.center = self.center;
        wave.borderColor = self.waveColor;
        wave.alpha = self.waveMaxAlpha;
        [self addSubview:wave];
    }
}

-(void)startAnimation{
    if (self.subviews.count == 0) {
        [self setup];
    }
    
    NSArray * subViews = self.subviews;
    CGFloat scale = self.bounds.size.width/self.waveWidth;
    
    for (int index = 0; index < subViews.count; index ++) {
        UIView * view = subViews[index];
        CGFloat delay = self.animationDuration/self.waveCount*index;
        [UIView animateWithDuration:self.animationDuration delay:delay options:self.animationOptions|UIViewAnimationOptionRepeat animations:^{
            view.transform = CGAffineTransformScale(view.transform, scale, scale);
            view.alpha = 0.0;
        } completion:nil];
    }
    
}
-(void)stopAnimation{
    for (UIView * view in self.subviews) {
        [view removeFromSuperview];
    }
}

@end




@implementation HSWaveView

-(void)setBorderColor:(UIColor *)borderColor{
    _borderColor = borderColor;
    [self setNeedsDisplay];
}

-(instancetype)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
    }
    return self;
}
-(instancetype)initWithCoder:(NSCoder *)aDecoder{
    self = [super initWithCoder:aDecoder];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
    }
    return self;
}

-(void)drawRect:(CGRect)rect{
    CGFloat redius = rect.size.width/2;
    UIBezierPath * path = [UIBezierPath bezierPathWithRoundedRect:rect cornerRadius:redius];
    path.lineWidth = 1.0;
    CAShapeLayer * layer = [CAShapeLayer layer];
    layer.path = path.CGPath;
    layer.strokeColor = self.borderColor.CGColor;
    layer.fillColor  = self.borderColor.CGColor;
    [self.layer addSublayer:layer];
}




@end






















