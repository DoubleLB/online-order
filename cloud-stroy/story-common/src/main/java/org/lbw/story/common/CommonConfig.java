package org.lbw.story.common;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan("org.lbw.story.common.Filter")
public class CommonConfig {}
