# router

A Clojurescript library designed to help routing in the browser.

## Usage

Include the dependency in your project.clj.

    [org.clojars.mkhoeini/router "0.1.0-SNAPSHOT"]

Then require the `router.core` in your cljs files.

    (ns your-namespace
      (:require [router.core :as router :include-macros true]))

Define your routes with the `match` macro. The syntax is of the [secretary](https://github.com/gf3/secretary).
However, it is simplified in that it will accept only a value, and doesn't pass parameters to the body.

    (router/match "/" :root-path)
    (router/match "/user/:id" :user-path)
    (router/match "/admin/*path" :admin-path)
    (router/match #"/search/([a-zA-Z]*)" :search-path)

Configure the history manager.

    (router/config!)

Or, pass some options. The defaults are:

    (router/config! {:use-fragment false    ;; Use '/#/some/path' style pathes
                     :path-prefix ""        ;; The path prefix. For example "!"
                     :enabled true})        ;; Enable the manager

Later on, you can enable or disable the manager with

    (router/set-enabled!)
    (router/set-disabled!)

And, finally you can register your callback to be called on routing.

    (router/set-callback! your-callback)

The callback must accept a single parameter. The parameter is a hashmap with two keys. `:value` and `:params`.
`:value` is the value you have associated with the route in the `match`. And `:params` is the hashmap of the
parameters of the route which secretary returns.

The current route is `(router/current-route)`. You can navigate to a new route with `(router/navigate! "/new/route")`.
Also, you can prevent the default behaviour of links and buttons from refreshing the window with
`(router/prevent-default-refresh!)`.
