package controllers;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller
{
    private final ActorSystem actorSystem;
    private final Materializer materializer;


    @Inject
    public HomeController(ActorSystem actorSystem, Materializer materializer)
    {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }


    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index()
    {
        return ok(views.html.index.render("TEST - index"));
    }


}
