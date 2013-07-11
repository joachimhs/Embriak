Embriak.KeysKeyRoute = Ember.Route.extend({
    model: function(key) {
        return Embriak.KeyModel.find(key.key_id);
    }
});

Embriak.KeysKeyController = Ember.ObjectController.extend({
    sortProperties: ['id'],

    doEdit: function() {
        var content = this.get('content');
        content.set('isEditing', true);
    },

    saveChanges: function() {
        var content = this.get('content');
        content.set('isEditing', false);
        content.save();
    },

    deleteKey: function() {
        var content = this.get('content');
        content.set('isEditing', false);
        content.deleteRecord();
    }
});
