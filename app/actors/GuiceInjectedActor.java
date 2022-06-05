package actors;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Injector;

public class GuiceInjectedActor implements IndirectActorProducer{

	private final Injector injector;
    private final Class<? extends Actor> actorClass;
    
    /**
     * Constructor
     * @param injector Guice injector
     * @param actorClass actor to be created
     */
    public GuiceInjectedActor(Injector injector, Class<? extends Actor> actorClass) {
        this.injector = injector;
        this.actorClass = actorClass;
    }
    
	@Override
	public Class<? extends Actor> actorClass() {
		return actorClass;
	}

	@Override
	public Actor produce() {
        return injector.getInstance(actorClass);
	}

}
