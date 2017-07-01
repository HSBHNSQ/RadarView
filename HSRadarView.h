//
//  HSRadarView.h
//  CloneData
//
//  Created by 何少博 on 2017/7/1.
//  Copyright © 2017年 shaobo.He. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HSRadarView : UIView

@property (nonatomic,assign) CGFloat waveWidth;
@property (nonatomic,assign) NSInteger waveCount;
@property (nonatomic,strong) UIColor *waveColor;
@property (nonatomic,assign) CGFloat waveMaxAlpha;
@property (nonatomic,assign) CGFloat animationDuration;
@property (nonatomic,assign) UIViewAnimationOptions animationOptions;


@end
