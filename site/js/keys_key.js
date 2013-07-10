Embriak.KeysKeyRoute = Ember.Route.extend({
    model: function(key) {
        return Embriak.KeyModel.find(key.key_id);
    }
});

Embriak.KeysKeyController = Ember.ObjectController.extend({
    sortProperties: ['id']
});
