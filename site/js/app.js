Ember.Application.reopen({
    init: function() {
        this._super();

        this.loadTemplates();
    },
    templates: [],

    loadTemplates: function() {
        var app = this,
            templates = this.get('templates');

        app.deferReadiness();

        var promises = templates.map(function(name) {
            return Ember.$.get('/templates/'+name+'.html').then(function(data) {
                Ember.TEMPLATES[name] = Ember.Handlebars.compile(data);
            });
        });

        Ember.RSVP.all(promises).then(function() {
            app.advanceReadiness();
        });
    }
});

var Embriak = Ember.Application.create({
    templates: ['buckets', 'keys', 'keys/key', 'header']
});

Embriak.Router.map(function () {
    this.resource("buckets", {path: "/"}, function () {
        this.resource("keys", {path: "/bucket/:bucket_id"}, function() {
            this.route("key", {path: "/:key_id"});
        });
    })
});

Ember.Handlebars.registerBoundHelper('jsonStringify', function(value) {
    if (value) return new Handlebars.SafeString("<pre>" + value + "</pre>");
    else return value;
});

