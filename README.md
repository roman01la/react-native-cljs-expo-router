An example of using [expo-router](https://docs.expo.dev/router/introduction/) in ClojureScript RN app.

## Upgrading existing project

1. Install expo-router in your project, [follow instructions here](https://docs.expo.dev/router/installation/#manual-installation)
2. Patch expo-router to point to JS entry point generated from your CLJS code
   - A patch for expo-router@4.0.17 is included in this repo [patches/expo-router+4.0.17.patch](patches/expo-router+4.0.17.patch)
   - Follow [this guide](https://dev.to/zhnedyalkow/the-easiest-way-to-patch-your-npm-package-4ece) to patch expo-router package
3. Add `:expo/root-ns app` setting in your `shadow-cljs.edn` build config
    - `app` is a "root ns" where compiler will look for `layout`, `tabs`, etc. namespaces
4. Make sure to copy and require `app.shadow` macro namespace, this is where we generate a fake "file system", called "context" in expo router.
5. Make sure that your `layout`, `tabs` and pages components are called `root`. Those root components will be automatically rendered by expo router.

> Unlike in Expo, none of cljs namespaces are automatically required, except project's entry point, which means you still have to require them somewhere. In this example `layout` namespaces at every level of routing hierarchy require neighboring namespaces and child layout namespaces.