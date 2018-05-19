//
//  NativeAdBig.h
//  MyAdProject
//
//  Created by stutid366 on 04/04/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MoPub.h"
#import "BaseView.h"

@interface NativeAdBig : BaseView
@property (strong, nonatomic) IBOutlet UIView *contentView;
@property (strong, nonatomic) IBOutlet UILabel *titleLabel;
@property (strong, nonatomic) IBOutlet UILabel *sponsored;
@property (strong, nonatomic) IBOutlet UIView *sponsoredContainer;
@property (strong, nonatomic) IBOutlet UILabel *mainTextLabel;
@property (strong, nonatomic) IBOutlet UILabel *callToActionLabel;
@property (strong, nonatomic) IBOutlet UIImageView *iconImageView;
@property (strong, nonatomic) IBOutlet UIImageView *mainImageView;
@property (strong, nonatomic) IBOutlet UIImageView *privacyInformationIconImageView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintTitleLabelWidth;

@end
