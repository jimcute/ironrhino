package org.ironrhino.core.remoting.impl;

import static org.ironrhino.core.metadata.Profiles.CLOUD;
import static org.ironrhino.core.metadata.Profiles.DEFAULT;
import static org.ironrhino.core.metadata.Profiles.DUAL;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.context.annotation.Profile;

@Singleton
@Named("serviceRegistry")
@Profile({ DEFAULT, DUAL, CLOUD })
public class StandaloneServiceRegistry extends AbstractServiceRegistry {

}
