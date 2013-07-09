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
            return Ember.$.get('/templates/'+name+'.hbs').then(function(data) {
                Ember.TEMPLATES[name] = Ember.Handlebars.compile(data);
            });
        });

        Ember.RSVP.all(promises).then(function() {
            app.advanceReadiness();
        });
    }
});

var Embriak = Ember.Application.create({
    templates: []
});

Embriak.Router.map(function () {
    this.resource("buckets", {path: "/"}, function () {
        this.resource("keys", {path: "/bucket/:bucket_id"});
    })
});

Embriak.BucketsRoute = Ember.Route.extend({
    model: function() {
        return Embriak.BucketModel.findAll();
    }
});

Embriak.KeysRoute = Ember.Route.extend({
    model: function(bucket) {
        console.log('Embriak.KeysRoute model: ' + bucket.bucket_id);
        return Embriak.BucketModel.find(bucket.bucket_id);
    }
});

Embriak.BucketsController = Ember.ArrayController.extend({});

Embriak.KeysController = Ember.ObjectController.extend({});

Ember.TEMPLATES['buckets'] = Ember.Handlebars.compile('' +
    '<div id="pageContent">' +
    '<div class="row">' +
        '<div class="span3 leftmenu">{{#each controller}}{{#linkTo keys this}}{{id}}{{/linkTo}}<br />{{/each}}</div>' +
        '<div class="span9">{{outlet}}</div>' +
    '</div>' +
    '</div>'
);

Ember.TEMPLATES['keys'] = Ember.Handlebars.compile('' +
    'KEYS<br/>' +
    '{{#each keys}}' +
        '{{id}} <br />' +
    '{{/each}}'
);