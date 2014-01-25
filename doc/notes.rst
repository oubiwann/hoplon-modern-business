# Features

## Remove the need to create HEAD boilerplate

Notes from IRC:

[19:16:26] <oubiwann>    I was thinking some more about this question: http://hoplon.discoursehosting.net/t/how-to-avoid-head-boilerplate-in-hoplon-projects-solved/32/2
[19:18:08] <oubiwann>    I don't know anything about the hoplon or clojurescript boot process... but is there any way to build *.inc.js files in clojurescript such that the are compiled in time to be prepended to main.js before the read of the hoplon code is?
[19:19:14] <michaniskin>     oubiwann: how do you mean build *.inc.js files in clojurescript?
[19:19:30] <michaniskin>     you want to prepend js files to the emitted main.js?
[19:19:48] <oubiwann>    right, but I don't want to create .js files, I'd like to write them as .cljs files
[19:19:56] <oubiwann>    have them compiled to .js, and then prepended...
[19:21:10] <michaniskin>     interesting
[19:21:16] <michaniskin>     but they're static right?
[19:21:26] <michaniskin>     like they only contain boilerplate?
[19:21:41] <michaniskin>     like a "prelude" of sorts?
[19:21:47] <oubiwann>    well, the title element might change...
[19:21:53] <oubiwann>    but that's all I can think of
[19:22:59] <michaniskin>     you can make a cljs project
[19:23:02] <michaniskin>     and just compile it
[19:23:11] <michaniskin>     and then extract the .js file you get there
[19:23:16] <oubiwann>    hehe
[19:23:18] <oubiwann>    okay
[19:23:25] <michaniskin>     and rename it foo.inc.js
[19:23:28] <oubiwann>    that'll work :-)
[19:23:34] <michaniskin>     but tell me more about what you want to do
[19:23:38] <michaniskin>     maybe there is another way
[19:23:48] <oubiwann>    1) I don't want to have to write .js :-)
[19:23:56] <michaniskin>     i don't blame you
[19:24:01] <michaniskin>     ;)
[19:24:23] <oubiwann>    2) If I'm going to have a site with multiple pages (possibly lots of content), I'd like to *just* change the content bits and titles
[19:26:25] <michaniskin>     i have a radical proposal
[19:26:32] <oubiwann>    ?
[19:26:45] <michaniskin>     a simple boot task that preprocesses your files, adding the boilerplate
[19:27:09] <michaniskin>     maybe make your file index.cljs.hlx
[19:27:10] <michaniskin>     or something
[19:27:26] <michaniskin>     your task could just insert the boilerplate
[19:27:38] <michaniskin>     and then emit a .hl file
[19:28:03] <michaniskin>     i mean the real problem is manipulating the head of the document at runtime
[19:28:13] <michaniskin>     so maybe there are alternatives there, too
[19:28:42] <oubiwann>    so, if I had 10 .hlx files, all of these would get processed by inserting the appropriate elements, and then 10 .hl files would get created?
[19:28:51] <michaniskin>     yeah
[19:29:05] <michaniskin>     you could then do like `boot watch mytask hoplon`
[19:29:12] <michaniskin>     where mytask is the task that does that

Later, the next day:

[14:31:44] <michaniskin>     so boot provides some things that you can use to manage your intermediate files, too
[14:31:56] <oubiwann>    oh yeah?
[14:31:58] <michaniskin>     like the .hl files you will produce with your task
[14:32:08] <oubiwann>    manage in what sense?
[14:32:09] <michaniskin>     those aren't exactly source files in that they're ephemeral
[14:32:16] oubiwann  nods
[14:32:17] <michaniskin>     you want them to be managed by boot
[14:32:28] <michaniskin>     with boot there is no such thing as a "clean" task
[14:32:39] <michaniskin>     because intermediate files are taken care of automatically
[14:32:52] <michaniskin>     there is a function in boot.core, mkdir! and mk!
[14:33:06] <michaniskin>     that creates a managed tempdir and tmpfile respectively
[14:33:27] <michaniskin>     what you can do is have your task create a tmpdir in which to emit the .hl files
[14:36:01] <oubiwann>    and then as boot continues on its way, after my task, it will find the generated .hl files in that tempdir, compile them, and prepend to main.js?
[14:36:25] <michaniskin>     and then add it to the :src-paths in your task
[14:36:27] <michaniskin>     yeah
[14:36:31] <oubiwann>    (or append, as the case may be...)
[14:36:57] <oubiwann>    ah, okay -- the temp dir(s) need to be in src-paths, got it
[14:37:01] <michaniskin>     in your task you can do (swap! boot update-in :src-paths into #{(.getPath my-temp-dir)})
[14:37:12] <michaniskin>     and before that
[14:37:15] <oubiwann>    oh, nice
[14:37:43] <michaniskin>     (let [my-temp-dir (mkdir! boot ::my-temp-dir) ...] (swap! boot ...) ...)
[14:37:49] oubiwann  nods
[14:37:51] <oubiwann>    perfect
[14:37:57] <oubiwann>    thanks so much for all this help :-)
[14:37:58] <michaniskin>     you can call mkdir! at any time after that
[14:38:02] <michaniskin>     like this
[14:38:11] <michaniskin>     (mkdir! boot ::my-temp-dir)
[14:38:25] <michaniskin>     if the tempdir already exists it will be emptied, but not removed
[14:38:33] <oubiwann>    okay
[14:38:36] <michaniskin>     this is useful in the "handler" part of the task
[14:38:42] <michaniskin>     so you can empty it before each run
[14:38:53] <michaniskin>     like if you're running multiple times in watch mode
[14:39:08] oubiwann  nods
[14:39:34] <michaniskin>     a cool thing about this is that you can get a reference to the tempdir, and then pass it to multiple tasks
[14:39:39] <michaniskin>     this is what the hoplon task does
[14:39:50] <michaniskin>     to coordinate output files with the cljs task
