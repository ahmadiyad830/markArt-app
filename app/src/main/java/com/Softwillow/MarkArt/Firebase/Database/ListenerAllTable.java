package com.Softwillow.MarkArt.Firebase.Database;

import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.Model.Setting;
import com.Softwillow.MarkArt.Model.Talents.Acting;
import com.Softwillow.MarkArt.Model.Talents.Drawer;
import com.Softwillow.MarkArt.Model.Talents.Signing;
import com.Softwillow.MarkArt.Model.Talents.Talent;
import com.Softwillow.MarkArt.Model.Talents.Writer;
import com.Softwillow.MarkArt.Model.User;

public interface ListenerAllTable extends FirebaseVar , ConditionIsReg {

    User user();

    Talent talent();

    Setting setting();

    Acting actor();

    Drawer drawer();

    Signing signing();

    Writer writer();

}
