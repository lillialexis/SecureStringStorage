package com.daoofdev.securestringstorage;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2017 Lilli Szafranski - Dao of Development, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * SecureStringStorage
 *
 * Created by Lilli Szafranski on 9/20/17.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.daoofdev.securestringstorage.SecureStringStorage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SecureStringStorageTests {
    private final static String TAG = "SecureStringStorageTests";

    private final static String ALIAS_VERSION = "4";

    @Test
    public void saveLoadDeleteString() throws Exception {
        String saveString = "This is a string";
        String alias = "saveLoadDeleteStringTest" + ALIAS_VERSION;

        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias, saveString);

        String loadString = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias);

        assertEquals(saveString, loadString);

        boolean success = SecureStringStorage.deleteStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias);

        assertTrue(success);

        loadString = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias);

        assertNull(loadString);
    }

    private void saveNullArguments(Context context, String alias, String string) {
        boolean exceptionWasThrown = false;

        try {
            SecureStringStorage.saveStringToSecureStorage(context, alias, string);
        } catch (IllegalArgumentException e1) {
            exceptionWasThrown = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        assertTrue(exceptionWasThrown);
    }

    private void loadNullArguments(Context context, String alias) {
        boolean exceptionWasThrown = false;

        try {
            SecureStringStorage.loadStringFromSecureStorage(context, alias);
        } catch (IllegalArgumentException e1) {
            exceptionWasThrown = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        assertTrue(exceptionWasThrown);
    }

    private void deleteNullArguments(Context context, String alias) {
        boolean exceptionWasThrown = false;

        try {
            SecureStringStorage.deleteStringFromSecureStorage(context, alias);
        } catch (IllegalArgumentException e1) {
            exceptionWasThrown = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        assertTrue(exceptionWasThrown);
    }

    @Test
    public void saveNullArguments() throws Exception {
        String saveString = "Another string";
        String alias = "saveNullArgsTest" + ALIAS_VERSION;

        saveNullArguments(InstrumentationRegistry.getTargetContext(), alias, null);
        saveNullArguments(InstrumentationRegistry.getTargetContext(), null, saveString);
        saveNullArguments(null, alias, saveString);
    }

    @Test
    public void loadNullArguments() throws Exception {
        String alias = "loadNullArgsTest" + ALIAS_VERSION;

        loadNullArguments(InstrumentationRegistry.getTargetContext(), null);
        loadNullArguments(null, alias);
    }

    @Test
    public void deleteNullArguments() throws Exception {
        String alias = "deleteNullArgsTest" + ALIAS_VERSION;

        deleteNullArguments(InstrumentationRegistry.getTargetContext(), null);
        deleteNullArguments(null, alias);
    }

    @Test
    public void saveEmptyString() throws Exception {
        String saveString = "";
        String alias = "saveEmptyStringTest" + ALIAS_VERSION;

        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias, saveString);

        String loadString = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias);

        assertEquals(saveString, loadString);
    }

    @Test
    public void saveLongString() throws Exception {
        String saveString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor ut labore et dolore magna aliqua. Duis at consectetur lorem donec massa sapien faucibus. Facilisi morbi tempus iaculis urna id volutpat lacus. Pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet. At risus viverra adipiscing at in tellus. Hac habitasse platea dictumst quisque. Ac turpis egestas maecenas pharetra convallis. Faucibus ornare suspendisse sed nisi. Turpis tincidunt id aliquet risus feugiat in. Habitant morbi tristique senectus et netus et malesuada fames ac. Non sodales neque sodales ut. Duis at tellus at urna. Egestas purus viverra accumsan in nisl nisi scelerisque eu ultrices. Felis bibendum ut tristique et egestas quis. Aliquet nec ullamcorper sit amet risus nullam. A arcu cursus vitae congue mauris rhoncus aenean vel. Placerat in egestas erat imperdiet sed euismod nisi. Consectetur lorem donec massa sapien faucibus. Ullamcorper malesuada proin libero nunc consequat interdum varius sit. Viverra suspendisse potenti nullam ac tortor vitae purus faucibus. Odio euismod lacinia at quis risus sed. Dictumst quisque sagittis purus sit amet volutpat consequat mauris nunc. Ut tellus elementum sagittis vitae et leo duis ut. Pharetra magna ac placerat vestibulum. Proin nibh nisl condimentum id venenatis a condimentum vitae. In hac habitasse platea dictumst quisque sagittis purus sit amet. Ultrices tincidunt arcu non sodales. Viverra orci sagittis eu volutpat odio facilisis mauris sit. Interdum velit laoreet id donec ultrices tincidunt. Malesuada fames ac turpis egestas maecenas pharetra convallis posuere. Tortor posuere ac ut consequat semper viverra nam. Bibendum enim facilisis gravida neque convallis a cras. Luctus venenatis lectus magna fringilla urna porttitor rhoncus dolor purus. Aliquam nulla facilisi cras fermentum odio eu feugiat pretium nibh. Faucibus purus in massa tempor nec feugiat nisl pretium fusce. Quam pellentesque nec nam aliquam. Amet cursus sit amet dictum sit amet justo. Orci dapibus ultrices in iaculis nunc. Sed arcu non odio euismod lacinia at quis risus sed. Habitant morbi tristique senectus et netus et malesuada fames. Enim ut sem viverra aliquet eget sit. Libero nunc consequat interdum varius. Sit amet mattis vulputate enim nulla aliquet porttitor lacus. Ultrices mi tempus imperdiet nulla malesuada pellentesque elit eget gravida. Enim nulla aliquet porttitor lacus luctus. Sodales ut eu sem integer. At in tellus integer feugiat scelerisque varius morbi. Faucibus a pellentesque sit amet porttitor eget dolor. Rhoncus aenean vel elit scelerisque mauris pellentesque. Leo urna molestie at elementum eu. Vel pharetra vel turpis nunc eget lorem dolor sed. Nisl suscipit adipiscing bibendum est. Nullam eget felis eget nunc lobortis mattis. Etiam dignissim diam quis enim lobortis scelerisque fermentum. Et magnis dis parturient montes nascetur ridiculus mus mauris. Duis convallis convallis tellus id interdum velit laoreet id donec. Nunc congue nisi vitae suscipit tellus mauris a. Ut eu sem integer vitae justo eget magna fermentum. Habitasse platea dictumst quisque sagittis purus sit amet volutpat consequat. Adipiscing commodo elit at imperdiet dui. Sagittis eu volutpat odio facilisis mauris sit. Nulla pellentesque dignissim enim sit. Id velit ut tortor pretium viverra suspendisse potenti nullam. Sit amet nulla facilisi morbi tempus iaculis urna id. Tempus imperdiet nulla malesuada pellentesque elit. Gravida arcu ac tortor dignissim. Proin libero nunc consequat interdum. Integer eget aliquet nibh praesent. Quam quisque id diam vel quam. Vitae nunc sed velit dignissim sodales ut eu sem. In nulla posuere sollicitudin aliquam ultrices. Vel orci porta non pulvinar neque laoreet suspendisse interdum consectetur. Eget mauris pharetra et ultrices neque ornare aenean. Elementum facilisis leo vel fringilla est ullamcorper eget nulla. Congue quisque egestas diam in arcu cursus. Sagittis orci a scelerisque purus semper. Nisl tincidunt eget nullam non nisi est sit amet facilisis. Adipiscing elit pellentesque habitant morbi tristique senectus. Platea dictumst vestibulum rhoncus est pellentesque. Malesuada nunc vel risus commodo viverra. Sagittis purus sit amet volutpat consequat mauris nunc congue nisi. Ut diam quam nulla porttitor massa id. Vulputate ut pharetra sit amet aliquam id diam maecenas. In fermentum et sollicitudin ac orci phasellus. Integer vitae justo eget magna fermentum iaculis eu non diam. Lorem donec massa sapien faucibus et molestie ac. Nulla aliquet enim tortor at auctor urna nunc. Nisl purus in mollis nunc sed id semper. Non blandit massa enim nec. In nibh mauris cursus mattis molestie. Imperdiet nulla malesuada pellentesque elit eget gravida. Vitae purus faucibus ornare suspendisse sed nisi lacus. Egestas quis ipsum suspendisse ultrices gravida dictum fusce ut placerat. Proin sagittis nisl rhoncus mattis rhoncus urna neque viverra justo. Dignissim convallis aenean et tortor at risus viverra adipiscing. Sit amet consectetur adipiscing elit pellentesque habitant morbi. Adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus. Ultricies mi eget mauris pharetra et ultrices. Nulla porttitor massa id neque aliquam vestibulum morbi blandit cursus. Id interdum velit laoreet id donec ultrices tincidunt arcu. Ipsum suspendisse ultrices gravida dictum fusce ut placerat orci nulla. Odio pellentesque diam volutpat commodo. Lobortis feugiat vivamus at augue eget arcu. Ipsum dolor sit amet consectetur adipiscing. Tristique risus nec feugiat in fermentum posuere urna nec tincidunt. Risus nec feugiat in fermentum posuere urna. Purus in mollis nunc sed id. Eget egestas purus viverra accumsan. Hac habitasse platea dictumst quisque sagittis. Pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet. Pharetra pharetra massa massa ultricies mi quis hendrerit. Maecenas sed enim ut sem viverra aliquet eget sit. Non sodales neque sodales ut etiam sit amet nisl. Turpis cursus in hac habitasse platea. Suspendisse interdum consectetur libero id faucibus nisl tincidunt eget nullam. Purus sit amet volutpat consequat mauris nunc congue nisi vitae. Dui vivamus arcu felis bibendum. Enim neque volutpat ac tincidunt vitae semper quis lectus. Facilisi etiam dignissim diam quis enim lobortis. Lacinia quis vel eros donec ac odio tempor orci dapibus. Nunc sed id semper risus in. Elementum sagittis vitae et leo duis ut diam quam. Tellus integer feugiat scelerisque varius morbi enim. Nisl pretium fusce id velit ut tortor pretium. Natoque penatibus et magnis dis parturient montes nascetur ridiculus mus. Lacus viverra vitae congue eu consequat. Metus aliquam eleifend mi in nulla posuere sollicitudin aliquam. Sit amet nisl suscipit adipiscing bibendum. Fringilla phasellus faucibus scelerisque eleifend donec pretium. Nibh praesent tristique magna sit amet purus gravida. Pharetra diam sit amet nisl. Dictum at tempor commodo ullamcorper a lacus vestibulum sed arcu. Nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet. Praesent tristique magna sit amet purus. Cras pulvinar mattis nunc sed. Sit amet volutpat consequat mauris nunc congue nisi vitae suscipit. Gravida rutrum quisque non tellus orci. Et netus et malesuada fames. Enim ut tellus elementum sagittis. Pulvinar elementum integer enim neque volutpat ac tincidunt vitae semper. Odio morbi quis commodo odio aenean sed adipiscing diam donec. Sit amet commodo nulla facilisi nullam. Pulvinar mattis nunc sed blandit libero. Risus viverra adipiscing at in tellus integer. Viverra orci sagittis eu volutpat odio facilisis mauris. Et malesuada fames ac turpis egestas integer eget aliquet. Aliquet eget sit amet tellus cras. Sit amet tellus cras adipiscing enim eu turpis. Ornare suspendisse sed nisi lacus. Natoque penatibus et magnis dis parturient montes nascetur. Senectus et netus et malesuada. Erat velit scelerisque in dictum non consectetur a. Urna duis convallis convallis tellus id. Venenatis lectus magna fringilla urna porttitor rhoncus dolor. Auctor eu augue ut lectus arcu bibendum at varius. Placerat orci nulla pellentesque dignissim enim sit amet. Porta lorem mollis aliquam ut porttitor leo a. Dignissim convallis aenean et tortor at risus viverra adipiscing. Enim facilisis gravida neque convallis a cras. Mauris commodo quis imperdiet massa tincidunt. Non sodales neque sodales ut etiam sit amet nisl. Cursus eget nunc scelerisque viverra. Elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue. Parturient montes nascetur ridiculus mus mauris vitae ultricies. Senectus et netus et malesuada fames ac turpis egestas maecenas. Condimentum mattis pellentesque id nibh. Hac habitasse platea dictumst vestibulum rhoncus est pellentesque elit. Libero volutpat sed cras ornare arcu dui vivamus. Facilisi etiam dignissim diam quis enim lobortis scelerisque. Tincidunt vitae semper quis lectus nulla at volutpat. Fames ac turpis egestas maecenas pharetra convallis posuere. Tellus pellentesque eu tincidunt tortor. Euismod lacinia at quis risus sed vulputate odio. Eu turpis egestas pretium aenean pharetra magna ac placerat vestibulum. Interdum consectetur libero id faucibus nisl tincidunt. Arcu bibendum at varius vel. Fusce id velit ut tortor. Faucibus interdum posuere lorem ipsum dolor. Pharetra diam sit amet nisl. Augue neque gravida in fermentum et sollicitudin. Ipsum nunc aliquet bibendum enim facilisis gravida neque convallis a. In hendrerit gravida rutrum quisque non tellus orci ac. Egestas integer eget aliquet nibh praesent tristique. Ipsum dolor sit amet consectetur adipiscing elit duis. Dolor sit amet consectetur adipiscing. Nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet. Porttitor massa id neque aliquam vestibulum. Aenean euismod elementum nisi quis eleifend quam. Placerat orci nulla pellentesque dignissim. Suspendisse ultrices gravida dictum fusce ut placerat orci nulla. Nec nam aliquam sem et tortor consequat id. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur. Tortor posuere ac ut consequat semper viverra nam libero justo. Ut diam quam nulla porttitor massa id neque aliquam vestibulum. Tincidunt tortor aliquam nulla facilisi cras fermentum odio eu. Magna fermentum iaculis eu non. Integer feugiat scelerisque varius morbi enim nunc. Consequat semper viverra nam libero. Aliquam etiam erat velit scelerisque in dictum non consectetur. Egestas maecenas pharetra convallis posuere morbi leo.";
        String alias = "saveLongStringTest" + ALIAS_VERSION;

        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias, saveString);

        String loadString = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias);

        assertEquals(saveString, loadString);

        // TODO: Doesn't work w RSA. Can pre-23 do AES, though? https://stackoverflow.com/a/10007285/955856
    }

    @Test
    public void saveLongishStrings() throws Exception {
        String saveString100 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore ";
        String saveString244 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis at consectetur lorem donec massa sapien faucibus. Facilisi morbi tempus iaculis urna id volutpat lacus. Pellentesqu";
        String saveString245 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis at consectetur lorem donec massa sapien faucibus. Facilisi morbi tempus iaculis urna id volutpat lacus. Pellentesque";
        String saveString246 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis at consectetur lorem donec massa sapien faucibus. Facilisi morbi tempus iaculis urna id volutpat lacus. Pellentesque ";
        String saveString256 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis at consectetur lorem donec massa sapien faucibus. Facilisi morbi tempus iaculis urna id volutpat lacus. Pellentesque adipiscing";

        String alias100 = "saveLongishStringsTest100" + ALIAS_VERSION;
        String alias244 = "saveLongishStringsTest244" + ALIAS_VERSION;
        String alias245 = "saveLongishStringsTest245" + ALIAS_VERSION;
        String alias246 = "saveLongishStringsTest246" + ALIAS_VERSION;
        String alias256 = "saveLongishStringsTest256" + ALIAS_VERSION;

        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias100, saveString100);
        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias244, saveString244);
        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias245, saveString245);
        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias246, saveString246);
        SecureStringStorage.saveStringToSecureStorage(InstrumentationRegistry.getTargetContext(), alias256, saveString256);

        String loadString100 = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias100);
        String loadString244 = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias244);
        String loadString245 = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias245);
        String loadString246 = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias246);
        String loadString256 = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias256);

        assertEquals(saveString100, loadString100);
        assertEquals(saveString244, loadString244);
        assertEquals(saveString245, loadString245);
        assertEquals(saveString246, loadString246);
        assertEquals(saveString256, loadString256);
    }

    @Test
    public void loadInvalidAlias() throws Exception {
        String alias = "loadInvalidAliasTest" + ALIAS_VERSION;

        String string = SecureStringStorage.loadStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias);

        assertNull(string);
    }

    @Test
    public void deleteInvalidAlias() throws Exception {
        String alias = "deleteInvalidAliasTest" + ALIAS_VERSION;

        boolean success = SecureStringStorage.deleteStringFromSecureStorage(InstrumentationRegistry.getTargetContext(), alias);

        assertFalse(success);
    }
}
